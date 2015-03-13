eval(loadFile("src/main/webapp/factorial.js"));

testCases(test,

	function test15() {
		assert.that(factorial(15), eq(1307674368000));
	},
	
	function testRegEx() {
	    var actual = "JUnit in Action";
		assert.that(actual, matches(/in/));
		assert.that(actual, not(matches(/out/)));
	}
	
);