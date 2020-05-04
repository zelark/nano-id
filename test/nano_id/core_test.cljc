(ns nano-id.core-test
  (:require #?(:clj  [clojure.test :refer [deftest is testing]])
            #?(:cljs [cljs.test :refer-macros [deftest is testing]])
    [nano-id.core :refer [nano-id custom alphabet]]))

(def nano-ids (repeatedly nano-id))

(defn ^boolean valid-id? [id]
  (re-matches #"^[A-Za-z0-9_-]+$" id))

(defn ^boolean close? [^double x ^double y]
  (let [precision 0.05]
    (< (Math/abs (- x y)) precision)))

;; Tests

(deftest basic-functionality
  (testing "generates URL-friendly IDs"
    (let [ids (take 10 nano-ids)]
      (doseq [id ids]
        (is (== (count id) 21) "test default length")
        (is (= (valid-id? id) id) "test id alphabet"))))

  (testing "has no collisions"
    (let [ids (take 100000 nano-ids)]
      (is (apply distinct? ids))))

  (testing "has flat distribution"
    (let [n      100000
          id-len (count (nano-id))
          ab-len (count alphabet)
          xf     (comp (map frequencies) (take n))
          chars  (transduce xf (partial merge-with +) {} nano-ids)]
      (doseq [[_ freq] chars]
        (let [distribution (/ (* freq ab-len)
                              (* n    id-len))]
          (is (close? distribution 1.0)))))))


(deftest test-custom-nano-id
  (testing "has flat distribution"
    (let [alphabet "abcdefghijklmnopqrstuvwxyz"
          ab-len   (count alphabet)
          id-len   5
          gen-id   (custom alphabet id-len)
          n        100000
          gen-ids  (repeatedly #(gen-id))
          xf       (comp (map frequencies) (take n))
          chars    (transduce xf (partial merge-with +) {} gen-ids)]
      (doseq [[_ freq] chars]
        (let [distribution (/ (* freq ab-len)
                              (* n    id-len))]
          (is (close? distribution 1.0))))))

  (testing "custom random"
    ;; x >> 6 is equal to x / 2^6
    ;; x & 63 is equal to x % 64
    (let [alphabet "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz"
          prng     (fn [n]
                     (->> 2377900801
                          (iterate #(unsigned-bit-shift-right % 6))
                          (take n)
                          reverse
                          #?(:clj byte-array)))
          gen-id   (custom alphabet 6 prng)]
      (is (= (gen-id) "1CiyB0")))))



(deftest test-exceptions
  (testing "size must be positive (nano-id)"
    (is (thrown? #?(:clj AssertionError :cljs js/Error) (nano-id -2))))
  (testing "size must be positive (custom)"
    (is (thrown? #?(:clj AssertionError :cljs js/Error) (custom "abc" -2))))
  (testing "low boundary of alphabet"
    (is (thrown? #?(:clj AssertionError :cljs js/Error) (custom "a" 10))))
  (testing "high boundary of alphabet"
    (is (thrown? #?(:clj AssertionError :cljs js/Error) (custom (repeat 257 "a") 10)))))
