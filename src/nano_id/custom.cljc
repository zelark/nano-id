(ns nano-id.custom
  (:require [nano-id.random :as rnd]))


(defn generate
  "Secure random ID generator with custom alphabet.
  Takes `alphabet` and returns a function of only one argument — `size`.
  `alphabet` must contain 256 symbols or less. Otherwise,
  the generator will not be secure. `random` (optional) is a custom
  random bytes generator, by default used `nano-id.random`."
  ([alphabet]
   (generate alphabet rnd/random-bytes))
  ([alphabet random]
   (assert (<= 2 (count alphabet) 256) "alphabet must contain from 2 to 256 characters.")
   (let [alphabet (vec (map str alphabet))
         power    (int (/ (Math/log (dec (count alphabet))) (Math/log 2)))
         mask     (dec (bit-shift-left 2 power))]
     (fn [size]
       (loop [step  (int (* 1.6 (/ mask (count alphabet)) size))
              bytes (random step)
              id    #?(:clj () :cljs "")]
         (if (== (count id) size)
           #?(:clj (clojure.string/join id) :cljs id)
           (recur step
                  (or (next bytes) (random step))
                  (if-let [ch (nth alphabet (bit-and (first bytes) mask) nil)]
                    #?(:clj (conj id ch) :cljs (str ch id))
                    id))))))))
  