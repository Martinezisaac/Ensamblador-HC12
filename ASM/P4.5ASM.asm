	ORG	%0100
UNO:	EQU	$30
DOS:	EQU	$505
LBL1:	DC.B	"ABC"
	LDY	#LBL1
	LDX	UNO
ET1:	LDAA	DOS
	LDAB	LBL1
	END
