(defproject openminded "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [com.stuartsierra/component "1.1.0"]]
  :main ^:skip-aot openminded.main
  :target-path "target/%s"
  :source-paths ["src"]
  :global-vars {*warn-on-reflection* true}
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev    {:dependencies [[org.clojure/test.check "1.1.1"]
                                     [org.clojure/tools.namespace "1.5.0"]]}
             :spec   {:source-paths ["spec"]}})
