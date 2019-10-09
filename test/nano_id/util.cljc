(ns nano-id.util)


(defn ^boolean valid-id? [id]
  (re-matches #"^[A-Za-z0-9_-]+$" id))


(defn ^boolean close? [^double x ^double y]
  (let [precision 0.05]
    (< (Math/abs (- x y)) precision)))
