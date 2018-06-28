(ns nano-id.core
  (:require [clojure.string :as str]
            [nano-id.random :as random]))


(def ^:const alphabet "_~0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")


(defn nano-id
  "Secure random ID generator.
  Generates IDs of the specified `size`, it's 21 by default."
  ([] (nano-id 21))
  ([size]
   (let [mask 0x3f
         id   (for [byte (random/random-bytes size)]
                (nth alphabet (bit-and byte mask)))]
     (str/join id))))
