(ns nano-id.core
  (:require [nano-id.random :as rnd]))


(def alphabet
  (mapv str "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"))


(defn nano-id
  "Secure random ID generator.
  Generates IDs of the specified `size`, it's 21 by default."
  ([] (nano-id 21))
  ([size]
   (let [mask  0x3f
         bytes (rnd/random-bytes size)]
     (->> bytes
          (map #(nth alphabet (bit-and % mask)))
          (apply str)))))

