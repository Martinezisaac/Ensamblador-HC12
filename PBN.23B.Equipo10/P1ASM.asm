; comentario número 1 =Programación=
	ORG 	%00001111
Et1: 	#EQU 	$FFFF
dos: 	LDAA 	@4732
	SWI
	DS.B 	%0011000011111100
; comentario número 2 ¡Bajo!
; comentario número 3 -Nivel
Tres: 	SWI
	END