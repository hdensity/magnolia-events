# Magnolia Events Management

[![GitHub](https://img.shields.io/github/license/hdensity/magnolia-events)](https://github.com/hdensity/magnolia-events/blob/master/LICENSE)
[![Build Status](https://travis-ci.com/hdensity/magnolia-events.svg?branch=master)](https://travis-ci.com/hdensity/magnolia-events)
[![Coverage Status](https://coveralls.io/repos/github/hdensity/magnolia-events/badge.svg?branch=master)](https://coveralls.io/github/hdensity/magnolia-events?branch=master)
[![Maintainability](https://api.codeclimate.com/v1/badges/eb25bda7c671750166ce/maintainability)](https://codeclimate.com/github/hdensity/magnolia-events/maintainability)
[![Code Climate issues](https://img.shields.io/codeclimate/issues/hdensity/magnolia-events)](https://codeclimate.com/github/hdensity/magnolia-events/issues)
[![Maven Central](https://img.shields.io/maven-central/v/it.schm.magnolia/magnolia-events)](https://search.maven.org/artifact/it.schm.magnolia/magnolia-events)
[![javadoc](https://javadoc.io/badge2/it.schm.magnolia/magnolia-events/javadoc.svg)](https://javadoc.io/doc/it.schm.magnolia/magnolia-events)
[![Active](http://img.shields.io/badge/Status-Active-green.svg)](https://github.com/hdensity/magnolia-events)

This project provides a simple event management module for Magnolia CMS.

## Installation

To install the Magnolia Events module, add a maven dependency to your [webapp bundle](https://documentation.magnolia-cms.com/display/DOCS62/Creating+a+custom+webapp+with+Maven):

```XML
<properties>
    <version.magnolia.events>1.0.0</version.magnolia.events>
</properties>

<dependency>
    <groupId>it.schm.magnolia</groupId>
    <artifactId>magnolia-events</artifactId>
    <version>${version.magnolia.events}</version>
</dependency>
```

Additionally, to ensure your custom configuration is applied after module defaults have already been loaded, add the following to your [module descriptor](https://documentation.magnolia-cms.com/display/DOCS62/How+to+create+and+use+a+custom+Magnolia+Maven+module+for+custom+Java+components#HowtocreateanduseacustomMagnoliaMavenmoduleforcustomJavacomponents-anc-runtime-dependenciesRuntimedependenciesinthemoduledescriptor), which is usually found under ```src/main/resources/META-INF/magnolia/<module-name>.xml```

```XML
<dependencies>
    <dependency>
        <name>events</name>
        <version>1.0.0/*</version>
    </dependency>
</dependencies>
```

## License

This project is licensed under the MIT License; see the [LICENSE](https://github.com/hdensity/magnolia-events/blob/master/LICENSE) file for details.

Copyright 2020 &copy; Sam Schmit-Van Werweke
