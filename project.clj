(defproject nano-id "1.0.0"
  :description "A tiny, secure, URL-friendly unique string ID generator"
  :url "https://github.com/zelark/nano-id"

  :license { :name "The MIT License"
             :url "https://opensource.org/licenses/MIT" }

  :plugins [[lein-doo       "0.1.10"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :javac-options ["-Xlint:unchecked" "-target" "1.8" "-source" "1.8"]
  :java-source-paths ["src"]

  :profiles {:dev {:dependencies [[org.clojure/clojure       "1.10.1"]
                                  [org.clojure/clojurescript "1.10.238"]
                                  ;; perf test
                                  [criterium "0.4.5"]
                                  [com.aventrix.jnanoid/jnanoid "2.0.0"]]}
             :perf {:jvm-opts ^:replace ["-server"
                                         "-Xms4096m"
                                         "-Xmx4096m"
                                         "-Dclojure.compiler.direct-linking=true"]}}

  :doo { :alias { :browsers [:chrome :firefox] }}

  :aliases { "deploy"    ["do" "clean," "deploy" "clojars"]
             "test"      ["do" ["clean"] ["test"]]
             "cljs-test" ["do" ["doo" "browsers" "test" "once"]]
             "bench"     ["with-profile" "default,dev,perf" "run" "-m" "nano-id.benchmark"]}

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
