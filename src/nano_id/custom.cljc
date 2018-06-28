(ns nano-id.custom
  (:require [clojure.string :as str]
            [nano-id.random :as random]))


(defn generate
  "Secure random ID generator with custom alphabet.
  Takes `alphabet` and returns a function which is ID generator.
  `alphabet` must contain 256 symbols or less. Otherwise,
  the generator will not be secure."
  [alphabet]
  (assert (<= 2 (count alphabet) 256) "alphabet must contain from 2 to 256 characters.")
  (let [alphabet-len (count alphabet)
        power        (int (/ (Math/log (dec alphabet-len)) (Math/log 2)))
        mask         (dec (bit-shift-left 2 power))]
    (fn [size]
      (let [step  (int (* 1.6 (/ mask alphabet-len) size))
            bytes (flatten (repeatedly #(random/random-bytes step)))]
        (reduce (fn [id byte]
                  (if (== (count (filter some? id)) size)
                    (reduced (str/join id))
                    (conj id (nth alphabet (bit-and byte mask) nil))))
                [] bytes)))))
