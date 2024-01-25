# nano-id

A tiny, secure, URL-friendly unique string ID generator for Clojure and ClojureScript.

- **Secure**. It uses cryptographically strong random APIs.
- **Compact**. It uses a larger alphabet than UUID (A-Za-z0-9_-). So ID size was reduced from 36 to 21 symbols.
- **URL-Firendly**. It uses only URL-friendly characters. Perfect for unique identifiers in web applications.

```clojure
clj -Sdeps '{:deps {nano-id/nano-id {:mvn/version "1.1.0"}}}'

(require '[nano-id.core :refer [nano-id]])
(nano-id) ;; => "trxwfoC8mqB3Q8Wrdq4OQ"
```

[![Clojars Project](https://img.shields.io/clojars/v/nano-id.svg)](https://clojars.org/nano-id)
[![cljdoc badge](https://cljdoc.org/badge/nano-id/nano-id)](https://cljdoc.org/d/nano-id/nano-id/CURRENT)
[![clojure tests](https://github.com/zelark/nano-id/actions/workflows/tests.yaml/badge.svg?branch=master)](https://github.com/zelark/nano-id/actions/workflows/tests.yaml)

## Benchmark

```bash
$ lein bench

## Actually, you will get more detailed info, this is summary of 3 runs.

UUID               0.29µs 0.29µs 0.30µs
nano-id            0.43µs 0.44µs 0.43µs
jnanoid            0.64µs 0.66µs 0.65µs
nano-id (custom)   0.62µs 0.62µs 0.62µs
jnanoid (custom)   0.65µs 0.65µs 0.68µs
```

Configuration:

- MacBook Pro (16-inch, 2019), 2,3 GHz 8-Core Intel Core i9, 32 GB 2667 MHz DDR4;
- OpenJDK Runtime Environment Temurin-11.0.22+7 (build 11.0.22+7);
- Clojure 1.11.1.

## Installation

### Clojure CLI

```text
nano-id/nano-id {:mvn/version "1.1.0"}
```

### Leiningen or Boot

```text
[nano-id "1.1.0"]
```

## Usage

The default implementation uses 64-character alphabet and generates 21-character IDs.

```clojure
user=> (require '[nano-id.core :refer [nano-id]])
nil

user=> (nano-id)
"NOBHihn110UuXbF2JiKxT"
```

If you want to reduce the ID size (and increase collision probability), you can pass the size as an argument.

```clojure
user=> (nano-id 10)
"N2g6IlJP0l"
```

Don’t forget to check the safety of your ID size via [collision probability calculator](https://zelark.github.io/nano-id-cc/).

### IE

For IE support, you need to add crypto alias:

```clojure
(ns your-app.polyfills)

(when-not (exists? js/crypto)
  (set! js/crypto js/msCrypto))
```

```clojure
(ns your-app.core
  (:require [your-app.polyfills]
            [nano-id.core :refer [nano-id]]))
```

### Node.js

If your target is node, use `@peculiar/webcrypto` polyfill:

```clojure
(ns your-app.polyfills
  (:require ["@peculiar/webcrypto" :refer [Crypto]]))

(set! js/crypto (Crypto.))
```

```clojure
(ns your-app.core
  (:require [your-app.polyfills]
            [nano-id.core :refer [nano-id]]))
```

### Custom ID generator

If for whatever reason the default implementation doesn't fit your project, you can build your own ID generator just passing your alphabet and ID size in `custom` function. It will give you back a new generator:

```clojure
user=> (require '[nano-id.core :refer [custom]])
nil

user=> (def my-nano-id (custom ".-" 6))
#'user/my-nano-id

user=> (my-nano-id)
"-.---."
```

Also you can provide your random bytes generator. In the example below we use this feature to encode the current time:

```clojure
(let [alphabet "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz"
      time-gen (fn [n]
                 (->> (quot (System/currentTimeMillis) 1000)
                      (iterate #(unsigned-bit-shift-right % 6))
                      (take n)
                      reverse
                      byte-array))
      time-id  (custom alphabet 6 time-gen)]
  (time-id))
"0TfMui"
```

This encodes current time using a lexicographical alphabet.

## Tools

- [Nano ID Collision Calculator](https://zelark.github.io/nano-id-cc/)

## Other implementations

You can find implementations in other programming languages [here](https://github.com/ai/nanoid#other-programming-languages).

## License

Copyright © 2018-2020 Aleksandr Zhuravlev.

Code released under the [MIT License](https://github.com/zelark/nano-id/blob/master/LICENSE).

Based on the original [Nano ID](https://github.com/ai/nanoid) by [Andrey Sitnik](https://github.com/ai/).
