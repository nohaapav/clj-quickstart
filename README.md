# clj-quickstart

A set of ready-to-use clojure templates for quick DEV kickoff.
 
* **web** - Web application (FE)
* **server** - Web application server (BE)
* **bundle** - Web application server serving web app (FE + BE)

## Setting up a development environment

Prerequisites
- Leiningen 2.8.2 or newer

Install local dependencies

```
lein deps
```

Start a REPL session

```
lein repl
```

and call function `(fig-start)`, which will start the figwheel server and watch based auto-compiler. App is served on http://localhost:3449

In order to use REPL on frontend side call additionally `(cljs-repl)`. Both `(fig-start)` & `(cljs-repl)` are
part of dev **repl.user** namespace.

## License

Copyright © 2020

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
