(ns nano-id.random
  #?(:clj (:import nano_id.NanoID))
  #?(:clj (:import java.util.Random)))

#?(:clj (set! *warn-on-reflection* true))
#?(:clj (def instance (delay (NanoID.))))

(defn random-bytes
  "Returns a random byte sequence of the specified size."
  [size]
  #?(:clj  (let [seed (byte-array size)]
             (.nextBytes ^Random (.getSecureRandom ^NanoID @instance) seed)
             seed)
     :cljs (let [seed (js/Uint8Array. size)]
             (.getRandomValues js/crypto seed)
             (array-seq seed))))
