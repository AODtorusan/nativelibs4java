// Le bloc ifdef suivant est la fa�on standard de cr�er des macros qui facilitent l'exportation 
// � partir d'une DLL. Tous les fichiers contenus dans cette DLL sont compil�s avec le symbole OTHERLIB_EXPORTS
// d�fini sur la ligne de commande. Ce symbole ne doit pas �tre d�fini dans les projets
// qui utilisent cette DLL. De cette mani�re, les autres projets dont les fichiers sources comprennent ce fichier consid�rent les fonctions 
// OTHERLIB_API comme �tant import�es � partir d'une DLL, tandis que cette DLL consid�re les symboles
// d�finis avec cette macro comme �tant export�s.
#ifdef OTHERLIB_EXPORTS
#define OTHERLIB_API __declspec(dllexport)
#else
#define OTHERLIB_API __declspec(dllimport)
#endif

// Cette classe est export�e de OtherLib.dll
class OTHERLIB_API COtherLib {
public:
	COtherLib(void);
	// TODO�: ajoutez ici vos m�thodes.
};

extern OTHERLIB_API int nOtherLib;

OTHERLIB_API int fnOtherLib(int, int);
