#!/usr/bin/env python3
"""Safely inspect ColorOS/OPlus/realme .theme packages."""
from __future__ import annotations

import argparse
import json
import sys
import zipfile
from pathlib import Path
import xml.etree.ElementTree as ET

TEXT_FIELDS = [
    "Author",
    "Summary",
    "Description",
    "UUID",
    "PackageName",
    "VersionName",
    "VersionCode",
    "EditorVersion",
    "LastModifyTime",
]


def text(root: ET.Element, tag: str) -> str:
    node = root.find(tag)
    return (node.text or "").strip() if node is not None else ""


def has_unsafe_path(name: str) -> bool:
    return name.startswith("/") or ".." in Path(name).parts


def inspect_theme(path: Path) -> dict:
    if not path.is_file():
        raise FileNotFoundError(f"Theme file not found: {path}")
    if path.suffix.lower() != ".theme":
        raise ValueError("Expected a .theme file")

    with zipfile.ZipFile(path) as archive:
        names = archive.namelist()
        unsafe = [name for name in names if has_unsafe_path(name)]
        if unsafe:
            raise ValueError(f"Unsafe archive paths found: {unsafe[:5]}")
        if "themeInfo.xml" not in names:
            raise ValueError("Missing themeInfo.xml")

        root = ET.fromstring(archive.read("themeInfo.xml"))
        files = [item for item in archive.infolist() if not item.is_dir()]
        previews = [
            item.filename
            for item in files
            if item.filename.lower().startswith("picture/")
            and item.filename.lower().endswith((".jpg", ".jpeg", ".png", ".webp"))
        ]

        return {
            "archiveName": path.name,
            "sizeBytes": path.stat().st_size,
            "rootElement": root.tag,
            "fields": {key: text(root, key) for key in TEXT_FIELDS},
            "packages": [
                {"name": node.attrib.get("name", ""), "version": node.attrib.get("version", "")}
                for node in root.findall("./packageInfo/package")
            ],
            "resolutions": [(node.text or "").strip() for node in root.findall("./resolutionInfo/resolution")],
            "entryCount": len(names),
            "fileCount": len(files),
            "hasWallpaper": "wallpaper" in names,
            "hasIcons": "icons" in names,
            "hasLauncher": any(name in names for name in ("com.oppo.launcher", "com.android.launcher")),
            "previews": previews,
        }


def main() -> int:
    parser = argparse.ArgumentParser(description="Inspect a ColorOS .theme package")
    parser.add_argument("theme", type=Path, help="Path to a .theme file")
    parser.add_argument("--pretty", action="store_true", help="Pretty-print JSON")
    args = parser.parse_args()
    print(json.dumps(inspect_theme(args.theme), indent=2 if args.pretty else None, ensure_ascii=False))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except Exception as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        raise SystemExit(1)
