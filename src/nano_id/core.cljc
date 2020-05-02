(ns nano-id.core
  (:require [nano-id.random :as rnd])
  #?(:clj (:import nano_id.NanoID)))

(def alphabet
  (mapv str "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"))

(defn nano-id
  "Secure random ID generator.
  Generates IDs of the specified size, 21 by default."
  ([]
   #?(:clj  (NanoID/nanoID)
      :cljs (nano-id 21)))
  ([size]
   #?(:clj  (NanoID/nanoID size)
      :cljs (let [mask 0x3f]
              (loop [bytes (rnd/random-bytes size)
                     id ""]
                   (if bytes
                     (recur (next bytes)
                            (->> (first bytes)
                                 (bit-and mask)
                                 alphabet
                                 (str id)))
                     id))))))

(defn custom
  "Builds ID generator with custom parameters.
  Takes alphabet and size. alphabet must contain 256 symbols or less; otherwise,
  the generator won't be secure.
  Also you can provide your own random bytes generator."
  ([alphabet size]
   (custom alphabet size rnd/random-bytes))
  ([alphabet size random]
   (assert (<= 2 (count alphabet) 256) "alphabet must contain from 2 to 256 characters.")
   (assert (pos? size) "size must be positive.")
   (let [alphabet      (mapv str alphabet)
         length        (count alphabet)
         power-of-two? (zero? (bit-and length (dec length))) ;; https://bit.ly/ispow2
         mask          (if power-of-two?
                         (dec length)
                         (dec (bit-shift-left 2 (int (/ (Math/log length) (Math/log 2))))))
         step          (if power-of-two?
                         size
                         (int (/ (* size mask 1.6) length)))]
     (fn []
       (loop [bytes (random step)
              id    #?(:clj (StringBuilder.) :cljs "")]
         (if (= (count id) size)
           #?(:clj (str id) :cljs id)
           (recur (or (next bytes) (random step))
                  (if-let [ch (nth alphabet (bit-and (first bytes) mask) nil)]
                    #?(:clj (.append id ch) :cljs (str id ch))
                    id))))))))
