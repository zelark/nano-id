# nano-id
A tiny, secure, URL-friendly unique string ID generator for both Clojure and ClojureScript.

[![Clojars Project](https://img.shields.io/clojars/v/nano-id.svg)](https://clojars.org/nano-id)
[![CircleCI](https://circleci.com/gh/zelark/nano-id/tree/master.svg?style=svg)](https://circleci.com/gh/zelark/nano-id/tree/master)

### Secure
nano-id uses [SecureRandom](https://docs.oracle.com/javase/7/docs/api/java/security/SecureRandom.html) (Clojure) and [crypto](https://developer.mozilla.org/en-US/docs/Web/API/Window/crypto) (ClojureScript) to generate cryptographically strong random IDs with a proper distribution of characters.

### Compact
nano-id generates compact IDs with just 21 characters. By using a larger alphabet than UUID, nano-id can generate a greater number of unique IDs, when compared to UUID, with fewer characters (21 versus 36).

### URL-Friendly
By default nano-id uses URL-friendly characters `[A-Za-z0-9_~]`. Perfect for unique identifiers in web applications.

## Usage
Add to your project.clj: `[nano-id "0.9.1"]`.

The default implementation uses 64-character alphabet and generates IDs with size 21.
```clojure
user=> (require '[nano-id.core :refer [nano-id]])
nil

user=> (nano-id)
"NOBHihn110UuXbF2JiKxT"

;; But you can pass the size
user=> (nano-id 10)
"N2g6IlJP0l"
```

Also you can create a generator with a custom alphabet
```clojure
user=> (require '[nano-id.custom :refer [generate]])
nil

user=> (def my-nano-id (generate "abc"))
#'user/my-nano-id

user=> (my-nano-id 10)
"abcbabacba"
```

## Copyright and license
Code copyright 2018 [nano-id authors](https://github.com/zelark/nano-id/graphs/contributors) and [Andrey Sitnik](https://github.com/ai). Code released under the [MIT License](https://github.com/zelark/nano-id/blob/master/LICENSE).

Based on the original [nanoid](https://github.com/ai/nanoid) for JavaScript by [Andrey Sitnik](https://github.com/ai/).
