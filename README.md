
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]

<br/>
<div align="center">
<h3 align="center">SparkCommons</h3>

  <a href="https://github.com/frosxt/SparkCommons">
    <img src="https://cdn.discordapp.com/attachments/560497603289153542/1091406068074172538/ETERNAL_DEVELOPMENT_NEW_-_PURPLE_ABSTRACT.png" alt="Logo" width="682.5" height="383.5">
  </a>

  <p align="center">
    Eternal Development Commons Library
    <br />
    <br />
    <a href="https://github.com/frosxt/SparkCommons/issues">Report Bug</a>
    ·
    <a href="https://github.com/frosxt/SparkCommons/issues">Request Feature</a>
  </p>
</div>

[![Downloads][downloads-shield]][downloads-url] [![Last Commit][commits-shield]][commits-url] [![Time](https://wakatime.com/badge/user/43415694-efe0-4a8e-b57c-ce409e69d660/project/d1e0e9c7-cd4f-47c5-bd26-4c95a1d226b7.svg)](https://wakatime.com/badge/user/43415694-efe0-4a8e-b57c-ce409e69d660/project/d1e0e9c7-cd4f-47c5-bd26-4c95a1d226b7)

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#project-information">Project Information</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#features">Features</a></li>
    <li>
      <a href="#usage">Usage</a>
      <ul>
        <li><a href="#maven-pomxml">Maven</a></li>
        <li><a href="#gradle-buildgradle">Gradle</a></li>
        <li><a href="#examples">Examples</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#faq">FAQ</a></li>
    <li><a href="#optimisation">Optimisation</a></li>
    <li><a href="#authors">Authors</a></li>
    <li><a href="#support">Support</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>

[contributors-shield]: https://img.shields.io/github/contributors/frosxt/SparkCommons.svg?style=for-the-badge
[contributors-url]: https://github.com/frosxt/SparkCommons/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/frosxt/SparkCommons.svg?style=for-the-badge
[forks-url]: https://github.com/frosxt/SparkCommons/network/members
[stars-shield]: https://img.shields.io/github/stars/frosxt/SparkCommons.svg?style=for-the-badge
[stars-url]: https://github.com/frosxt/SparkCommons/stargazers
[issues-shield]: https://img.shields.io/github/issues/frosxt/SparkCommons.svg?style=for-the-badge
[issues-url]: https://github.com/frosxt/SparkCommons/issues
[downloads-shield]: https://img.shields.io/github/downloads/frosxt/SparkCommons/total
[downloads-url]: https://github.com/frosxt/SparkCommons/releases
[commits-shield]: https://img.shields.io/github/last-commit/frosxt/SparkCommons
[commits-url]: https://github.com/frosxt/commits/master
# SparkCommons

A library designed to help develop spigot plugins and eliminate redudant code within a project, and across projects. Aims to speed up the development of new projects by removing repetition. Designed to be optimised and be as minimal as possible on performance.

### Built with
* ![Java][Java]
* ![Spigot][Spigot]
* ![Apache Maven][Maven]

[Java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=oracle&logoColor=white
[Spigot]: https://img.shields.io/badge/Spigot-yellow.svg?style=for-the-badge&logo=minecraft&logoColor=white
[Maven]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
## Features

- Item Builder
- Placeholder Builder
- Legacy Minecraft Version Checker
- JSON Data Storage
- File Manager
- YAML Config Files
- Multiple Utilities
- Menu Support (Paginated & Non-paginated)


## Usage
SparkCommons is run alongside another plugin which utilises this library. Any plugin can utilise this library by adding it as a dependency to your project.

#### Maven (pom.xml)
```maven
<dependency>
    <groupId>me.frost</groupId>
    <artifactId>commons</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>system</scope>
    <systemPath>PUT PATH TO JAR FILE HERE</systemPath>
</dependency>
```
#### Gradle (build.gradle)
```gradle
dependencies {
    implementation(files("PATH TO FILE"))
}
```
#### Examples
The library can be used through usages such as:
```java
new ItemBuilder(...)
```
```java
new ColouredString(String textToConvert)
```
```java
NumberUtils#isInteger(String arguments)
```
```java
public class ExamplePagedMenu extends PaginatedMenu {
    public ExamplePagedMenu(Player player) {
        super(...)
    }
    
    @Override
    public void onClose(InventoryCloseEvent event) {
        MenuHandler.getInstance().getMenus().remove(event.getPlayer());
    }
}
```

## Roadmap

- [ ] Command Handler

- [ ] NBTAPI (Supporting all versions)

- [ ] Methods to handle things across minecraft versions

- [ ] Message Builder

## FAQ

#### Who is this meant for?

This is meant for developers to make the development of minecraft plugins easier. Server owners don't need to do anything with this except install it on servers which utilise this library.

#### Will this have x feature or support?

If there is a specific feature or support for something that you'd like to see, feel free to make a suggestion by opening an issue.


## Optimisations

This library is designed to be as optimised as possible and provide the most cost-free methods of getting stuff done. Performance is a priority with this library as-with all other products.


## Authors

- [@frosxt](https://www.github.com/frosxt)


## Support

For support, you can message me privately on discord @ muhammad#4616.


## Acknowledgements
* [CoasterFreakDE](https://github.com/CoasterFreakDE/minecraft-spigot-rgb-chat-support) - RGBChat integration
* [CryptoMorin](https://github.com/CryptoMorin) - XSeries (Titles, ActionBar and other utilities)
