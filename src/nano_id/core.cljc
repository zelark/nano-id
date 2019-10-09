(ns nano-id.core
  (:require [nano-id.random :as rnd]))


(def alphabet
  (vec (map str "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")))


(defn nano-id
  "Secure random ID generator.
  Generates IDs of the specified `size`, it's 21 by default."
  ([] (nano-id 21))
  ([size]
   (loop [mask  0x3f
          bytes (rnd/random-bytes size)
          id    #?(:clj (StringBuilder.) :cljs "")]
     (if bytes
       (recur mask
              (next bytes)
              (let [ch (nth alphabet (bit-and (first bytes) mask))]
                #?(:clj (.append id ch) :cljs (str id ch))))
       (str id)))))

