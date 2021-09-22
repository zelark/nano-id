(ns nano-id.random
  #?(:clj (:import nano_id.NanoID))
  #?(:clj (:import java.util.Random)))

#?(:clj (set! *warn-on-reflection* true))

(def ^:private secure-random
  #?(:clj  (delay NanoID/secureRandom)
     :cljs js/crypto))

(defn random-bytes
  "Returns a random byte sequence of the specified size."
  [size]
  #?(:clj  (let [seed (byte-array size)]
             (.nextBytes ^Random @secure-random seed)
             seed)
     :cljs (let [seed (js/Uint8Array. size)]
             (.getRandomValues secure-random seed)
             (array-seq seed))))
