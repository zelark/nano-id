(ns nano-id.core
  (:require [clojure.string :as str])
  (:import  #?(:clj java.security.SecureRandom)))


(def ^:const alphabet "_~0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")


(def secure-random
  #?(:clj  (SecureRandom.)
     :cljs js/crypto))


(defn ^:private rand-bytes
  "Returns a random byte array of the specified size."
  [size]
  #?(:clj  (let [seed (byte-array size)]
             (.nextBytes secure-random seed)
             seed)
     :cljs (let [seed (js/Uint8Array. size)]
             (.getRandomValues secure-random seed)
             (array-seq seed))))


(defn nano-id
  ([]
   (nano-id 21))
  ([size]
   (-> (for [byte (rand-bytes size)] (nth alphabet (bit-and byte 0x3f)))
       str/join)))       
