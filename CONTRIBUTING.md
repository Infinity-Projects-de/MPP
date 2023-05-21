# 🔧 Contributing

If you want to contribute by fixing an issue or adding new content to the project, you are more than
welcome to do so. For major changes or new content, please open an issue first to discuss what
you would like to change/add. If you can't add new things on your own but have any content
suggestions,
you can create an issue too. Please note the following Contributor License Agreement (CLA) before
getting started:

## 📜 Contributor License Agreement

By making contributions to this repository you are hereby agreeing that:

- You grant us and other users the right to use your contributions under one of the following
  respective licenses:
    - [All Rights Reserved](https://en.wikipedia.org/wiki/All_rights_reserved) for contributed or
      updated assets in `/src/main/resources`.
    - [GPL v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html) for code or other changes.
- Your contributions are of your own work and are free of legal restrictions (such as patents or
  copyrights).

If you have any questions about these terms, please get in contact with us. **If you do not agree to
these terms, please do not submit contributions to this repository.**

## 💬 Communication

For most code or project-related matters, it is recommended to reach out to Chechu, as he
oversees code management and project coordination within the team. Feel free to approach us
with any questions, concerns, or tasks related to the codebase or project. As the Project
and Code Manager, he is here to assist and ensure smooth progress in our development efforts.

If you are an external developer collaborating with our team, we don't have a specific designated
channel for you in our [Discord server](https://discord.gg/cVzz2cF4ST). However, we encourage
you to use the general channel #chat for communication purposes. If you need to get in touch
with me or have any specific questions or concerns, please feel free to mention me in the channel.
Alternatively, you can contact me privately or use GitHub for project-related discussions and
collaboration. We value effective communication and are committed to ensuring a smooth
collaboration experience for all team members, both internal and external.

## 🤖 We Use [GitHub Flow](https://docs.github.com/en/get-started/quickstart/github-flow), So All Code Changes Happen Through Pull Requests

Pull requests are the best way to propose changes to the codebase (we
use [GitHub Flow](https://guides.github.com/introduction/flow/index.html)). We actively welcome your
pull requests:

1. Fork the repo and create your branch from `./gradlew master`. You don't need to run BuildTools or
   any other setup, everything is set up using Gradle tasks.
2. Make your changes.
3. Make sure to build and test the plugin extensively. You can run the `./gradlew runServer` Gradle
   Task, which builds the plugin and starts a local test server for you. Be sure to use the local
   resource pack hosting server. See [below](#-how-we-handle-the-custom-resource-pack) for more
   information
4. We use [ktlint](https://github.com/pinterest/ktlint) to enforce the kotlin code styleguide.
   Please run the `./gradlew ktlintFormat` Gradle task to check your code.
5. Please use [Conventional Commit Messages](https://www.conventionalcommits.org/en/v1.0.0/) for
   your Commits.
6. Issue your pull request!

## 📦 How we handle the custom Resource Pack

MPP uses a custom Resource Pack that needed for the custom blocks and items. You can generate
this pack by running the `./gradlew buildResourcePack` Gradle Task.

The plugin first checks for a Resource Pack with the correct version locally and then on GitHub
Releases. If no pack is found an error is logged. The plugin version is calculated from the
commit amount on the current branch.

If you are in a development or testing environment you should run the local Resource Pack
hosting server with the `./gradlew runResourcePackServer` Gradle Task. This is to make sure that
the plugin always finds and uses the matching Resource Pack.

## 💻 Development

The plugin is being developed in Kotlin due to many improvements to the code, however that doesn't
limit the usage of Java, although we recommend Kotlin for a better consistency. Learning Kotlin is
quite easy thanks to its similarities with Java just in case you are not familiar. We're using
Gradle (Kotlin) as the toolchain.

When using Kotlin, we highly recommend using [Ktlint](https://ktlint.github.io/), which is
conveniently installed in our development environments, making it a comfortable choice. Ktlint is a
tool that automatically follows common coding conventions for Kotlin, such as
the [official Kotlin coding style guide](https://kotlinlang.org/docs/coding-conventions.html). These
conventions are quite easy to manually follow, but Ktlint can be incredibly helpful in catching and
correcting many common mistakes.

While we highly discourage the use of Java in this project, we understand that some developers might
not feel comfortable learning Kotlin despite it's ease of use. In such cases, we recommend following
some common coding conventions for Java, such
as [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

Additionally, it's important to note that incorporating Java code into a primarily Kotlin project
can result in slower PR reviews. This is because we will need to translate the Java code to Kotlin
beforehand. Therefore, using Kotlin as the primary language not only aligns with our development
preferences but also helps streamline the review process and ensures better collaboration.

To ensure code quality and maintainability, we adhere to best practices such as code reviews, unit
testing, and continuous integration.

* Code reviews play a crucial role in our development process. Pull Requests (PRs) are used to
  facilitate code review, where team members provide valuable feedback, suggestions, and ensure
  adherence to coding standards. This collaborative process not only improves code quality but also
  fosters knowledge sharing among team members.

* For efficient development, we recommend utilizing Intellij IDEA as the preferred Integrated
  Development Environment (IDE). Its robust features, excellent support for Kotlin (and Java), and
  seamless integration with Gradle and other tools make it a powerful choice for working on our
  project.

* Testing is an integral part of software development, even though testing Minecraft-specific code
  might have its challenges. Nonetheless, we encourage thorough testing whenever possible. Here are
  some key aspects of testing practices we emphasize:

    * Unit Testing: Unit tests focus on testing individual components, methods, and classes in
      isolation
      to verify their correctness and expected behavior. It is recommended to create well-structured
      code that allows for testing specific parts not directly related to Minecraft without the need
      for
      extensive mocking.
    * Integration Testing: Integration tests help identify potential conflicts or issues that may
      arise
      when multiple components work together. In some cases, the usage of mocking tools may be
      necessary
      to simulate dependencies or external interactions.
    * Mocking: Mocking tools can be utilized to simulate the behavior of external dependencies or
      complex systems during testing, enabling more comprehensive and effective testing.
    * Test Coverage: Test coverage refers to the percentage of code that is covered by tests. We
      strive
      to achieve as high test coverage as possible to ensure that critical parts of the codebase are
      thoroughly tested.
    * Continuous Integration (CI): Our team employs continuous integration practices to
      automatically
      build, test, and verify the codebase with each change committed to the repository. This
      ensures
      early detection of any issues and promotes a smooth and reliable development workflow.

## 🏗️ Architecture and design

By adopting a modular architecture, MPP divides the codebase into separate modules or components
that have well-defined responsibilities and boundaries. Each module focuses on a specific
functionality or feature, making it easier to understand, develop, test, and maintain. This modular
structure also enables independent development and deployment of modules, allowing for parallel work
and better team collaboration.

The modular architecture of MPP provides several benefits. Firstly, it improves the maintainability
of the project by reducing code entanglement and enforcing encapsulation. This means that changes or
updates to a specific module have minimal impact on other parts of the system, reducing the risk of
unintended side effects.

Secondly, the modular approach enhances scalability. New features or functionalities can be added by
creating new modules that interact with existing modules through well-defined interfaces. This
modularity allows for flexibility in expanding the project's capabilities without causing
disruptions or introducing unnecessary complexity.

Furthermore, the modular architecture facilitates code reuse. Common functionality or components can
be encapsulated into separate modules, which can be shared across different parts of the project.
This promotes consistency, reduces duplication of code, and improves overall development efficiency.

In the future, we will delve deeper into the specifics of MPP's modular architecture, exploring the
different modules, their responsibilities, and how they interact with each other. This will provide
a comprehensive understanding of the project's architecture and how it supports scalability,
maintainability, and extensibility.

## 👁️ Coding standards

Maintaining consistent coding standards is essential for ensuring code readability, enhancing
collaboration, and promoting code quality within our project. We follow a set of coding standards
and conventions to maintain a cohesive codebase. Here are some key aspects of our coding standards:

* Naming Conventions: We adhere to consistent naming conventions for variables, methods, classes,
  and other code elements. Descriptive and meaningful names are encouraged to improve code
  understandability.

* Code Formatting: We utilize tools like Ktlint and IDE formatters to enforce consistent code
  formatting. These tools automatically apply coding style rules, such as indentation, spacing, and
  line length, ensuring a uniform code appearance throughout the project.

* Documentation Comments: We value comprehensive documentation to enhance code understanding and
  maintainability. Documenting classes, methods, and important code blocks using meaningful comments
  helps other developers understand the purpose, behavior, and usage of the code.

* Code Modularity: We promote modular design principles to ensure separation of concerns and
  maintainability. Encapsulation and proper code organization help create reusable and maintainable
  code modules.

* Code Review: Code reviews are an integral part of our development process. We encourage all
  developers to actively participate in code reviews to ensure adherence to coding standards,
  identify potential issues, and exchange knowledge among team members.

By following these coding standards, you'll contribute to the consistency, readability, and
maintainability of our codebase. Remember to review existing code, seek feedback from other team
members, and continuously improve your coding skills to align with the project's coding standards.

Feel free to ask any questions or seek clarification on specific coding standards or conventions.

