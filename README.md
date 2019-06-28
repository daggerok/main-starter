# main-starter [![Build Status](https://travis-ci.org/daggerok/main-starter.svg?branch=clojure)](https://travis-ci.org/daggerok/main-starter)
Clojure starter using lein build tool.

## getting started

```bash
brew reinstall leiningen
git clone --no-single-branch --depth=1 https://github.com/daggerok/main-starter.git -b clojure
cd main-starter
rm -rf .git
# ...
```

## Usage

_testing_

    $ lein test

_fat jar_

    $ lein uberjar
    $ java -jar target/uberjar/*-standalone.jar

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter) branch: [clojure](https://github.com/daggerok/main-starter/tree/clojure/)_
