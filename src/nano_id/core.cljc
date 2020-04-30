(ns nano-id.core
  #?(:clj (:import nano_id.NanoID)))

#?(:cljs (require '[nano-id.random :as rnd]))

(def alphabet
  (mapv str "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"))

(defn nano-id
  "Secure random ID generator.
  Generates IDs of the specified `size`, it's 21 by default."
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
                     (str id)))))))
