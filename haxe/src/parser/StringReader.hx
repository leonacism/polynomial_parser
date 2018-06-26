package parser;

abstract StringReader(String) {
	public inline function new(data:String) this = data;
	
	@:to
	public inline function to() {
		var i = 0;
		return {
			hasNext: function() return i < this.length,
			next: function() return this.charAt(i++),
			peek: function() return this.charAt(i),
		};
	}
}