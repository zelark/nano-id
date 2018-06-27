(ns nano-id.random
  (:import  #?(:clj java.security.SecureRandom)))


(def ^:private secure-random
  #?(:clj  (SecureRandom.)
     :cljs js/crypto))


(defn random-bytes
  "Returns a random byte array of the specified size."
  [size]
  #?(:clj  (let [seed (byte-array size)]
             (.nextBytes secure-random seed)
             (seq seed))
     :cljs (let [seed (js/Uint8Array. size)]
             (.getRandomValues secure-random seed)
             (array-seq seed))))
