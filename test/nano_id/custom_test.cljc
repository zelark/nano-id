(ns nano-id.custom-test
  (:require
    #?(:clj  [clojure.test :refer :all]
       :cljs [cljs.test :refer-macros [deftest is testing]])
    [nano-id.util :as util]
    [nano-id.custom :refer [generate]]))


(deftest test-custom-nano-id
  (testing "has flat distribution"
    (let [alphabet "abcdefghijklmnopqrstuvwxyz"
          nano-id  (generate alphabet)
          nano-ids (repeatedly #(nano-id 5))
          n        100000
          ids      (take n nano-ids)
          id-len   (count (first ids))
          ab-len   (count alphabet)
          chars    (->> ids
                        (map frequencies)
                        (reduce #(merge-with + %1 %2) {}))]
      (doseq [[_ freq] chars]
        (let [distribution (/ (* freq ab-len)
                              (* n    id-len))]
          (is (util/close? distribution 1.0))))))

  (testing "custom random"
    (let [alphabet "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~"
          prng     (fn [n] (take n (iterate #(bit-shift-right % 6) 2377900801)))
          gen-id   (generate alphabet prng)]
      (is (= (gen-id 6) "1CzjD2")))))
