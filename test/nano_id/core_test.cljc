(ns nano-id.core-test
  (:require
    #?(:clj  [clojure.test :refer :all]
       :cljs [cljs.test :refer-macros [deftest is testing]])
    [nano-id.core :refer [nano-id alphabet]]
    [nano-id.util :as util]))


(def nano-ids (repeatedly nano-id))


(deftest basic-functionality
  (testing "generagets URL-friendly IDs"
    (let [ids (take 10 nano-ids)]
      (doseq [id ids]
        (is (== (count id) 21) "test default length")
        (is (= (util/valid-id? id) id) "test id alphabet"))))

  (testing "has no collisions"
    (let [ids (take 100000 nano-ids)]
      (is (apply distinct? ids))))
      
  (testing "has flat distribution")
    (let [n      100000
          ids    (take n nano-ids)
          id-len (count (nano-id))
          ab-len (count alphabet)
          chars  (->> ids 
                      (map frequencies)
                      (reduce #(merge-with + %1 %2) {}))]
      (doseq [[_ freq] chars]
        (let [distribution (/ (* freq ab-len)
                              (* n    id-len))]
          (is (util/close? distribution 1.0))))))
