# 🔧 Contributing
If you want to contribute by fixing an issue or adding new content to the project, you are more than welcome to do so. For major changes or new content, please open an issue first to discuss what you would like to change/add. If you can't add new things on your own but have any content suggestions, you can create an issue too. Please note the following Contributor License Agreement (CLA) before getting started:

## Contributor License Agreement
By making contributions to this repository you are hereby agreeing that:
- You grant us and other users the right to use your contributions under one of the following respective licenses:
    - [All Rights Reserved](https://en.wikipedia.org/wiki/All_rights_reserved) for contributed or updated assets in `/src/main/resources`.
    - [GPL v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html) for code or other changes.
- Your contributions are of your own work and are free of legal restrictions (such as patents or copyrights).

If you have any questions about these terms, please get in contact with us. **If you do not agree to these terms, please do not submit contributions to this repository.**

## We Use [GitHub Flow](https://guides.github.com/introduction/flow/index.html), So All Code Changes Happen Through Pull Requests
Pull requests are the best way to propose changes to the codebase (we use [GitHub Flow](https://guides.github.com/introduction/flow/index.html)). We actively welcome your pull requests:

1. Fork the repo and create your branch from `master`. You don't need to run BuildTools or any other setup, everything is set up using Gradle tasks.
2. Make your changes.
3. Make sure to build and test the plugin extensively. You can run the `runServer` Gradle Task, which builds the plugin and starts a local test server for you. Be sure to use the local resource pack hosting server. See [below](#how-we-handle-the-custom-resource-pack) for more information
4. We use [ktlint](https://github.com/pinterest/ktlint) to enforce the kotlin code styleguides. Please run the `ktlintFormat` Gradle task to check your code.
5. Please use [Conventional Commit Messages](https://www.conventionalcommits.org/en/v1.0.0/) for your Commits.
6. Issue your pull request!

## How we handle the custom Resource Pack
MPP uses a custom Resource Pack that needed for the custom blocks and items. You can generate this pack by running the `buildResourcePack` Gradle Task.

The plugin first checks for a Resource Pack with the correct version locally and then on GitHub Releases. If no pack is found an error is logged. The plugin version is calculated from the commit amount on the current branch.

If you are in a development or testing environment you should run the local Resource Pack hosting server with the `runResourcePackServer` Gradle Task. This is to make sure that the plugin always finds and uses the matching Resource Pack.
