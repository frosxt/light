
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

[![Downloads][downloads-shield]][downloads-url] [![Last Commit][commits-shield]][commits-url] [![wakatime](https://wakatime.com/badge/user/43415694-efe0-4a8e-b57c-ce409e69d660/project/d1e0e9c7-cd4f-47c5-bd26-4c95a1d226b7.svg)](https://wakatime.com/badge/user/43415694-efe0-4a8e-b57c-ce409e69d660/project/d1e0e9c7-cd4f-47c5-bd26-4c95a1d226b7&v=2) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/b3d0d7dc9fc8471fbf1810596106b4ae)](https://app.codacy.com/gh/frosxt/SparkCommons/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

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

A library designed to help develop spigot plugins and eliminate redudant code within a project, and across projects. Aims to speed up the development of new projects by removing repetition. Designed to be optimised and  as minimal as possible on performance. Supports minecraft versions from 1.8 to the latest.

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
- Menu Support (Paginated & non-paginated)
- Command Handler (Support for sub commands)
- Message Handler (Caches messages for optimal performance, supports titles, actionbar and sounds)


## Usage
SparkCommons is run alongside another plugin which utilises this library. Any plugin can utilise this library by adding it as a dependency to your project.

#### Maven (pom.xml)
```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
	<url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.frosxt</groupId>
    <artifactId>SparkCommons</artifactId>
    <version>v1.4.3</version>
</dependency>
```
#### Gradle (build.gradle)
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.frosxt:SparkCommons:v1.4.3'
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
        super(player, new ColouredString("&8&lEXAMPLE PAGINATED MENU").toString(), 18, 2);
        
        updateMenu();
    }
    
    @Override
    public void setup() {
        setTitle("&8&lEXAMPLE PAGINATED MENU")
        setFillerItem(new ItemBuilder(...).build());
        addFiller(FillingType.EMPTY_SLOTS);
      
        for (int i = 0; i < 27; i++) {
            buttons[i] = new Button(XMaterial.matchXMaterial("COMPASS").get().parseMaterial())
                    .setDisplayName("&eSlot: " + i)
                    .setClickAction(event -> event.setCancelled(true));
        }

        setButton(slot, page, new Button(...));
    }
}
```

## Roadmap

- [ ] NBTAPI (Supporting all versions)

## FAQ

#### Who is this meant for?

This is meant for developers to make the development of minecraft plugins easier. Server owners don't need to do anything with this except install it as a plugin on servers which utilise this library.

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
* [NoSequel](https://github.com/NoSequel/MenuAPI) - Menu Handler is heavily based on this, but not entirely the same
* Command Handler (From an old shared project)
