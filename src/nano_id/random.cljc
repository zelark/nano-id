(ns nano-id.random
  #?(:clj (:import java.security.SecureRandom)))

#?(:clj (defonce *secure-random (delay (SecureRandom.))))

(defn random-bytes
  "Returns a random byte sequence of the specified size."
  [size]
  #?(:clj  (let [seed (byte-array size)]
             (.nextBytes ^SecureRandom @*secure-random seed)
             seed)
     :cljs (let [seed (js/Uint8Array. size)]
             (.getRandomValues js/crypto seed)
             (array-seq seed))))
