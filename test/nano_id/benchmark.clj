(ns nano-id.benchmark
  (:require [criterium.core :as criterium]
            [nano-id.core :as nano-id])
  (:import com.aventrix.jnanoid.jnanoid.NanoIdUtils)
  (:import java.util.UUID))

(def magenta "\u001B[35m")
(def green   "\u001B[32m")
(def reset   "\u001B[0m")

(defn raw-title [color text]
  (println (str color "##### " text " #####" reset)))

(def title (partial raw-title magenta))

(defmacro bench! [name & body]
  `(do
     (title ~name)
     (let [{[mean#] :mean :as res#} (criterium/quick-benchmark (do ~@body) nil)]
       (println)
       (println green (format "%.2fµs" (* 1000000 mean#)) reset)
       (println)
       (criterium/report-result res#))
     (println)))

(defn run-perf-test []
  (let [size          10
        alphabet      "1234567890abcdefg"
        custom-title  (str " (custom - alphabet: " (count alphabet) ", size: " size ")")
        nano-custom   (nano-id/custom alphabet size)
        j-alphabet    (.toCharArray alphabet)]
    (bench!
     "UUID"
     (str (UUID/randomUUID))) ; randomUUID returns just an instance,
                              ; so we need to turn it into a string
                              ; to get more accurate result.
    (bench!
     "nano-id"
     (nano-id/nano-id))
    (bench!
     "jnanoid"
     (NanoIdUtils/randomNanoId))
    (bench!
     (str "nano-id" custom-title)
     (nano-custom))
    (bench!
     (str "jnanoid" custom-title)
     (NanoIdUtils/randomNanoId NanoIdUtils/DEFAULT_NUMBER_GENERATOR j-alphabet size))))

(defn -main [& _]
  (run-perf-test))

(comment
  (criterium/quick-benchmark (nano-id/nano-id) nil)
  (NanoIdUtils/randomNanoId)
  (nano-id/nano-id)
  (UUID/randomUUID)
  (-main))
