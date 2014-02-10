function changeLocale(combo) {
	var selectedItem = combo.options[combo.selectedIndex];
	window.location.href = "?lang=" + selectedItem.value;
}