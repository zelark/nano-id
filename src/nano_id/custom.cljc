(ns nano-id.custom
  (:require [clojure.string :as str]
            [nano-id.random :as random]))


(defn nano-id
  "Secure random ID generator with custom `alphabet`.
  `alphabet` must contain 256 symbols or less. Otherwise,
  the generator will not be secure. `size` defines ID length."
  [alphabet size]
  (let [alphabet-len (count alphabet)
        power        (int (/ (Math/log (dec alphabet-len)) (Math/log 2)))
        mask         (dec (bit-shift-left 2 power))
        step         (int (* 1.6 (/ mask alphabet-len) size))
        bytes        (flatten (repeatedly #(random/random-bytes step)))]
    (reduce (fn [id byte]
              (if (== (count (filter some? id)) size)
                (reduced (str/join id))
                (conj id (nth alphabet (bit-and byte mask) nil))))
            [] bytes)))
