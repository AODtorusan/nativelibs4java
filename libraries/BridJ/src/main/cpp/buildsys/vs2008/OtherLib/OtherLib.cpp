// OtherLib.cpp�: d�finit les fonctions export�es pour l'application DLL.
//

#include "stdafx.h"
#include "OtherLib.h"


// Il s'agit d'un exemple de variable export�e
OTHERLIB_API int nOtherLib=0;

// Il s'agit d'un exemple de fonction export�e.
OTHERLIB_API int fnOtherLib(int a, int b)
{
	return a + b;
}

// Il s'agit du constructeur d'une classe qui a �t� export�e.
// consultez OtherLib.h pour la d�finition de la classe
COtherLib::COtherLib()
{
	return;
}
