eval(loadFile("src/main/webapp/factorial.js"));

testCases(test,

	function test0() {
		assert.that(factorial(0), eq(1));
	},
	
	function test1() {
		assert.that(factorial(1), eq(1));
	},
	
	function test2() {
		assert.that(factorial(2), eq(2));
	},
	
	function test3() {
		assert.that(factorial(3), eq(6));
	},
	
	function test4() {
		assert.that(factorial(4), eq(24));
	},
	
	function test5() {
		assert.that(factorial(5), eq(120));
	},
	
	function test6() {
		assert.that(factorial(6), eq(720));
	},
	
	function test7() {
		assert.that(factorial(7), eq(5040));
	},
	
	function test8() {
		assert.that(factorial(8), eq(40320));
	},
	
	function test9() {
		assert.that(factorial(9), eq(362880));
	},
	
	function test10() {
		assert.that(factorial(10), eq(3628800));
	},
	
	function test11() {
		assert.that(factorial(11), eq(39916800));
	},
	
	function test12() {
		assert.that(factorial(12), eq(479001600));
	},
	
	function test13() {
		assert.that(factorial(13), eq(6227020800));
	},
	
	function test14() {
		assert.that(factorial(14), eq(87178291200));
	},
	
	function test15() {
		assert.that(factorial(15), eq(1307674368000));
	},
	
	function test16() {
		assert.that(factorial(16), eq(20922789888000));
	}
	
);