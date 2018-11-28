# nano-id
A tiny, secure, URL-friendly unique string ID generator for both Clojure and ClojureScript.

[![Clojars Project](https://img.shields.io/clojars/v/nano-id.svg)](https://clojars.org/nano-id)
[![cljdoc badge](https://cljdoc.org/badge/nano-id/nano-id)](https://cljdoc.org/d/nano-id/nano-id/CURRENT)
[![CircleCI](https://circleci.com/gh/zelark/nano-id/tree/master.svg?style=svg)](https://circleci.com/gh/zelark/nano-id/tree/master)

### Secure
nano-id uses [SecureRandom](https://docs.oracle.com/javase/7/docs/api/java/security/SecureRandom.html) (Clojure) and [crypto](https://developer.mozilla.org/en-US/docs/Web/API/Window/crypto) (ClojureScript) to generate cryptographically strong random IDs with a proper distribution of characters.

### Compact
nano-id generates compact IDs with just 21 characters. By using a larger alphabet than UUID, nano-id can generate a greater number of unique IDs, when compared to UUID, with fewer characters (21 versus 36).

### URL-Friendly
nano-id uses URL-friendly characters `[A-Za-z0-9_~]`. Perfect for unique identifiers in web applications.

## Usage
### Normal
Add to your project.clj: `[nano-id "0.9.3"]`.

The default implementation uses 64-character alphabet and generates 21-character IDs.
```clojure
user=> (require '[nano-id.core :refer [nano-id]])
nil

user=> (nano-id)
"NOBHihn110UuXbF2JiKxT"
```

But you can pass the size
```clojure
user=> (nano-id 10)
"N2g6IlJP0l"
```

### Custom alphabet or random number generator
Also you can provide your own alphabet as follow
```clojure
user=> (require '[nano-id.custom :refer [generate]])
nil

user=> (def my-nano-id (generate "abc"))
#'user/my-nano-id

user=> (my-nano-id 10)
"abcbabacba"
```

Or your custom random number generator, for example
```clojure
(let [alphabet "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~"
      time-gen (fn [n] (take n (iterate #(unsigned-bit-shift-right % 6)
                                        (quot (System/currentTimeMillis) 1000))))
      time-id  (generate alphabet time-gen)]
  (time-id 6))
"1RJu2O"

```
This encodes current time using a lexicographical alphabet.

## Tools
- [Nano ID Collision Calculator](https://zelark.github.io/nano-id-cc/)

## Copyright and license
Code copyright 2018 [nano-id authors](https://github.com/zelark/nano-id/graphs/contributors) and [Andrey Sitnik](https://github.com/ai). Code released under the [MIT License](https://github.com/zelark/nano-id/blob/master/LICENSE).

Based on the original [nanoid](https://github.com/ai/nanoid) for JavaScript by [Andrey Sitnik](https://github.com/ai/).
