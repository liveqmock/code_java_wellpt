function evalField(name){
	return $("#readFields").get(0);
}
function evalLaSource(laSource, poObject, psArrayName) {
	eval("laSource = poObject." + psArrayName);
	return laSource;
}

function evalPoObject(poObject, psArrayName, laNew) {
	eval("poObject." + psArrayName + " = laNew");
}

function evalLsFormula(loObject, lsFormula) {
	eval(lsFormula);
}
function evalLoFrom1(poFrom, loObject) {
	eval("poFrom." + loObject.fromWay + "Curves[poFrom." + loObject.fromWay
			+ "Curves.length]=loObject;");
}
function evalLoTo1(poTo, loObject) {
	eval("poTo." + loObject.toWay + "Curves[poTo." + loObject.toWay
			+ "Curves.length]=loObject;");
}
function evalLoFrom2(loFrom, loObject) {
	eval("loFrom." + loObject.toWay + "Curves[loFrom." + loObject.toWay
			+ "Curves.length]=loObject;");
}
function evalLoTo2(loTo, loObject) {
	eval("loTo." + loObject.fromWay + "Curves[loTo." + loObject.fromWay
			+ "Curves.length]=loObject;");
}
function evalPoFrom(poFrom, loObject) {
	eval("poFrom." + poFrom.cursorWay + "Curves[poFrom." + poFrom.cursorWay
			+ "Curves.length]=loObject;");
}
function evalPoTo(poTo, loObject) {
	eval("poTo." + poTo.cursorWay + "Curves[poTo." + poTo.cursorWay
			+ "Curves.length]=loObject;");
}