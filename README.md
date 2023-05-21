# MPP - Mega Plugin Project

**Welcome to the Mega Plugin Project GitHub repository!**

MPP is a Minecraft plugin for Paper servers that aims to including a lot of features to the game,
such as new dimensions, blocks, items, systems like a spell system and rituals... Currently, the
plugin only features custom items, weapons, armors, and blocks, as well as custom mobs. However,
most of that is only obtainable via debug commands, and only some terrain blocks generate in the
aether dimension.

This plugin is [**Open Source**](#-contribute), and is being actively worked on by our
[team](#-team).

## 📥 Download

You can download the latest development builds from [here](https://github.com/dm432/MPP/releases).

**Please note:**

- Only the newest version of [Paper](https://papermc.io/) is supported. **Spigot builds will not
  work!**
- This plugin is still in early development and as such bugs can occur.

## 🐛 Report bugs or other issues

If you're running into bugs or other problems, feel free to open an issue on
our [issue tracker](https://github.com/dm432/MPP/issues). When doing so, make sure to use one of the
provided templates and fill out all the requested information. Make sure to keep your issue's
description clear and concise. Your issue's title should also be easy to digest, giving our
developers and reporters a good idea of what's wrong without including too many details.

## 🔨How to build

If you're a user, we advise you not to build the plugin on your own due to a possible
misconfiguration of the resource pack.

We use Gradle as our toolchain, so building the plugin is very easy.

`./gradlew build` is used to build both the plugin and the resource pack

`./gradlew buildResourcepack` is used to only build the resource pack

The plugin is built in `./build/libs` and the resource pack in `./build/resourcepack`.

**Notice:**
You usually won't need the resource pack to be built and installed as a user, however, if you're a
developer and/or an artist that made a change on the resource pack or made a commit (local or
remote) you will have to run `./gradlew runResourcepackServer` so the plugin detects there's a local
resource pack. The reason behind is that this plugin usually uses GitHub's releases to download the
resource pack, that means the commit version must match and that not hosted (locally) changes to the
resource pack will have no effect.

## 🔧 Contribute

This project is open source, therefore, anyone can contribute to the plugin. If you want to
contribute please take a look at
our [Contribution Guide](https://github.com/dm432/MPP/blob/master/CONTRIBUTING.md). Please, take a
look at this file thoroughly as well.

## 👥 Team

MPP's team is currently small and consists of key members involved in different roles. The team
includes Charly, the owner of Infinity Projects and, therefore, the leader/boss of MPP.
Additionally, there is Chechu (myself), serving as the Project and Code Manager as well as a Senior
Developer. Lastly, Kabo, a Senior Developer and former Project Manager, is also part of the team.

experience for all team members, both internal and external.

## 📜 License information

[![Asset license (Unlicensed)](https://img.shields.io/badge/assets%20license-All%20Rights%20Reserved-red.svg?style=flat-square)](https://en.wikipedia.org/wiki/All_rights_reserved)
[![Code license (GPL v3.0)](https://img.shields.io/badge/code%20license-GPL%20v3.0-green.svg?style=flat-square)](https://github.com/TheRealKabo/MPP/blob/master/LICENSE)

You are free to create a gameplay video/review, extension or addon, parody, or any other fan work of
your own for MPP. However, if you are thinking about using MPP project's code or assets, please note
our licensing. All assets of MPP are unlicensed, and all rights are reserved to them their
respective authors. The source code of MPP is under the GPL v3.0 license.
