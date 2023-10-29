	ORG	$4040
et1:	ADCA	#$FFt
	ADCA	#$15l
	ADCA	#@
	ADCA	#ñ
	ADDD	#%1010
	ADDD	#@1238
	ADDD	#$FFFt
	ADDD	#$ABC
	EORA	$6
	ORG	%11
	EORA 	65536,X
	EORA 	[1,U]
	EORA 	[-1,U]
	EORA 	[-1,X]
	EQU	$ABH
tepa:	EQU	65536
	CBA
	CLC	
	CLI	
	EQU
	END