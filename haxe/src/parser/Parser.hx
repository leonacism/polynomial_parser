package parser;
import parser.Token.Type;
import parser.node.*;

class Parser {
	private var tree:MainNode;
	
	private var tr:{hasNext:Void -> Bool, next:Void -> Token, peek:Void -> Token};

	public function new() {}
	
	public function parse(tokens:Token) {
		this.tr = new TokenReader(tokens);
		this.tree = new MainNode();
		
		tree.expr = parseExpression();
		
		getData(tr.next(), Type.EOF);
		
		return tree;
	}
	
	private function parseExpression():ExpressionNode {
		var lhs = parseUnaryExpression();
		return parseBinaryExpression(lhs, 0);
	}
	
	private function parseBinaryExpression(lhs:ExpressionNode, minPrec:Int):ExpressionNode {
		var prec:Int;
		
		while((prec = getPrec(tr.peek())) >= minPrec) {
			var op = switch(getData(tr.next(), Type.Symbol(null))) {
				case '+' : '@add';
				case '-' : '@sub';
				case '*' : '@mul';
				case '/' : '@div';
				case '%' : '@mod';
				case _ : throw 'unexpected token : ${getData(tr.next(), Type.Symbol(null))}';
			}
			var rhs = parseTermExpression();
			
			if (getPrec(tr.peek()) > prec) rhs = parseBinaryExpression(rhs, prec+1);
			
			var ben = new BinaryExpressionNode();
			ben.lhs = lhs;
			ben.op = op;
			ben.rhs = rhs;
			lhs = ben;
		}
		
		return lhs;
	}
	
	private function getPrec(token:Token) {
		var op = is(token, Type.Symbol(null))? getData(tr.peek(), Type.Symbol(null)) : null;
		var a = Constants.OPERATOR_PRECEDENCES;
		for(i in 0...a.length) for(b in a[i]) if(op==b) return i;
		return -1;
	}
	
	private function parseUnaryExpression():ExpressionNode {
		if(is(tr.peek(), Type.Symbol('+'))) tr.next();
		else if(is(tr.peek(), Type.Symbol('-'))) {
			var uen = new UnaryExpressionNode();
			tr.next(); uen.op = '@neg';
			uen.rhs = parseTermExpression();
			return uen;
		}
		
		return parseTermExpression();
	}
	
	private function parseTermExpression():ExpressionNode {
		var en:ExpressionNode = new ExpressionNode();
		
		if(is(tr.peek(), Type.Identifier(null))) en = parseIdentifierExpression();
		else if(is(tr.peek(), Type.Symbol('('))) en = parseParenExpression();
		else if(is(tr.peek(), Type.Number(null))) en = parseNumberExpression();
		else throw 'unexpected token : ${tr.next()}';
		
		if(is(tr.peek(), Type.Symbol('^'))) {
			var ten = new TermExpressionNode();
			ten.lhs = en;
			tr.next(); ten.op = '@pow';
			ten.rhs = parseNumberExpression();
			return ten;
		}
		
		return en;
	}
	
	private function parseIdentifierExpression():ExpressionNode {
		var ien = new IdentifierExpressionNode();
		ien.identifier = getData(tr.next(), Type.Identifier(null));
		return ien;
	}
	
	private function parseNumberExpression():ExpressionNode {
		var nen = new NumberExpressionNode();
		nen.number = getData(tr.next(), Type.Number(null));
		return nen;
	}
	
	private function parseParenExpression():ExpressionNode {
		getData(tr.next(), Type.Symbol('('));
		var en = parseExpression();
		getData(tr.next(), Type.Symbol(')'));
		return en;
	}
	
	private function getData(token:Token, expected:Type):String {
		return switch(token.type) {
			case Type.Number(data) :
				switch(expected) {
					case Type.Number(data_) if(data==data_&&data_!=null || data_==null) : data;
					case _ : throw 'unexpected token : $token';
				}
			case Type.Identifier(data) :
				switch(expected) {
					case Type.Identifier(data_) if (data == data_ && data_ != null || data_ == null) : data;
					case _ : throw 'unexpected token : $token';
				}
			case Type.Keyword(data) :
				switch(expected) {
					case Type.Keyword(data_) if(data==data_&&data_!=null || data_==null) : data;
					case _ : throw 'unexpected token : $token';
				}
			case Type.Symbol(data) :
				switch(expected) {
					case Type.Symbol(data_) if(data==data_&&data_!=null || data_==null) : data;
					case _ : throw 'unexpected token : $token';
				}
			case Type.EOF :
				switch(expected) {
					case Type.EOF : 'EOF';
					case _ : throw 'unexpected token : $token';
				}
		}
	}
	
	private function is(token:Token, expected:Type):Bool {
		return switch(token.type) {
			case Type.Number(data) :
				switch(expected) {
					case Type.Number(data_) if(data==data_&&data_!=null || data_==null) : true;
					case _ : false;
				}
			case Type.Identifier(data) :
				switch(expected) {
					case Type.Identifier(data_) if(data==data_&&data_!=null || data_==null) : true;
					case _ : false;
				}
			case Type.Keyword(data) :
				switch(expected) {
					case Type.Keyword(data_) if(data==data_&&data_!=null || data_==null) : true;
					case _ : false;
				}
			case Type.Symbol(data) :
				switch(expected) {
					case Type.Symbol(data_) if(data==data_&&data_!=null || data_==null) : true;
					case _ : false;
				}
			case Type.EOF :
				switch(expected) {
					case Type.EOF : true;
					case _ : false;
				}
		}
	}
}