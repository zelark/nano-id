(ns nano-id.custom
  (:require [nano-id.random :as random]))


(defn generate
  "Secure random ID generator with custom alphabet.
  Takes `alphabet` and returns a function which is ID generator.
  `alphabet` must contain 256 symbols or less. Otherwise,
  the generator will not be secure."
  [alphabet]
  (assert (<= 2 (count alphabet) 256) "alphabet must contain from 2 to 256 characters.")
  (let [alphabet (vec (map str alphabet))
        power    (int (/ (Math/log (dec (count alphabet))) (Math/log 2)))
        mask     (dec (bit-shift-left 2 power))]
    (fn [size]
      (loop [step  (int (* 1.6 (/ mask (count alphabet)) size))
             bytes (random/random-bytes step)
             id    #?(:clj (StringBuilder.) :cljs "")]
        (if (== (.length id) size)
          (str id)
          (recur step
                 (or (next bytes) (random/random-bytes step))
                 (if-let [ch (nth alphabet (bit-and (first bytes) mask) nil)]
                   #?(:clj (.append id ch) :cljs (str id ch))
                   id)))))))
  