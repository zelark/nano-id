(ns nano-id.util)


(defn valid-id? [id]
  (re-matches #"^[A-Za-z0-9_~]+$" id))


(defn close? [^double x ^double y]
  (let [precision 0.05]
    (< (Math/abs (- x y)) precision)))
