(ns nano-id.core
  (:require [nano-id.random :as rnd]))

(def alphabet
  (mapv str "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"))

(defn nano-id
  "Secure random ID generator.
  Generates IDs of the specified `size`, it's 21 by default."
  ([] (nano-id 21))
  ([size]
   #?(:clj
      (let [mask 0x3f
            bytes (clojure.lang.RT/iter ^bytes (rnd/random-bytes size))]
        (loop [id (StringBuilder.)]
          (if (.hasNext bytes)
            (recur (let [ch (alphabet (bit-and (.next bytes) mask))]
                     (.append id ch)))
            (str id))))
      :cljs
      (let [mask 0x3f]
        (loop [bytes (rnd/random-bytes size)
               id ""]
             (if bytes
               (recur (next bytes)
                      (let [ch (nth alphabet (bit-and (first bytes) mask))]
                        (str id ch)))
               id))))))
