(defproject nano-id "0.11.0"
  :description "A tiny, secure, URL-friendly unique string ID generator"
  :url "https://github.com/zelark/nano-id"

  :license { :name "The MIT License"
             :url "https://opensource.org/licenses/MIT" }

  :plugins [[lein-doo       "0.1.10"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :profiles {
    :dev {
      :dependencies [[org.clojure/clojure       "1.9.0"]
                     [org.clojure/clojurescript "1.10.238"]] }}

  :doo { :alias { :browsers [:chrome :firefox] }}

  :aliases { "deploy"    ["do" "clean," "deploy" "clojars"]
             "test"      ["do" ["clean"] ["test"]]
             "cljs-test" ["do" ["doo" "browsers" "test" "once"]] }

  :cljsbuild {
    :builds [
      { :id "test"
        :source-paths ["src" "test"]
        :compiler { :main          nano-id.runner
                    :output-to     "target/unit-test.js"
                    :optimizations :advanced }}
    ]
  }
)
