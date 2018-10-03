(ns nano-id.core
  (:require [nano-id.random :as rnd]))


(def alphabet
  (vec (map str "_~0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")))


(defn nano-id
  "Secure random ID generator.
  Generates IDs of the specified `size`, it's 21 by default."
  ([] (nano-id 21))
  ([size]
   (->> (rnd/random-bytes size)
        (map #(nth alphabet (bit-and % 0x3f)))
        (apply str))))
