Tax Calculation Engine
======================
Contains the code and cli to run UK tax calculations.


How to use the CLI
------------------
At present the only way to create the CLI is to clone and build the software locally. In future the 
CLI will be downloadable as a GitHub release.

You will need a JDK installed - if you feel like it you could use
[GraalVM](https://github.com/graalvm/graalvm-ce-builds/releases/).

**Steps**
1. Build the project using maven `./mvnw clean package` or `./mvnw -Pnative clean package` if you
   want a native build.
2. This should leave you with a file called `calculation-engine-core...-shaded.jar` in 
   `core/target`.
3. Create a scenarios folder (the CLI assumes a folder in the same directory as the jar called
   `scenarios`) and add scenarios in separate files using the [YAML format](#YAML-format).
4. Run the CLI using `java -jar calc*shaded.jar <blank OR path to scenarios>.
5. You should get an output file in the same directory as the jar called `output.txt`.


YAML format
-----------
You can see an example, along with information about what to put in each field at
[core/src/test/resources/sample.yml](core/src/test/resources/sample.yml).


Repository Layout
-----------------

### `core` ###
Contains engine code and model. This is in its own module rather than at the project root so that 
it is easier in future to build out web/service layers.


Contributing
------------
There is not yet a well-developed process for contributions/pull-requests, releases or (in-time)
automated deployment of any web-hosted components. In the meantime, feel free to raise issues
or pull-requests!