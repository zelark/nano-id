(ns nano-id.core
  (:require [clojure.string :as str])
  (:import
    #?(:clj java.security.SecureRandom)))


(def ^:const alphabet "_~0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")


(defn ^:private rand-bytes
  "Returns a random byte array of the specified size."
  [size]
  #?(:clj  (let [seed (byte-array size)]
             (.nextBytes (SecureRandom.) seed)
             seed)
     :cljs (let [seed (js/Uint8Array. size)]
             (.getRandomValues js/crypto. seed)
             (array-seq seed))))


(defn nano-id
  ([]
   (nano-id 21))
  ([size]
   (-> (for [byte (rand-bytes size)] (nth alphabet (bit-and byte 0x3f)))
       str/join)))          
