// Le bloc ifdef suivant est la fa�on standard de cr�er des macros qui facilitent l'exportation 
// � partir d'une DLL. Tous les fichiers contenus dans cette DLL sont compil�s avec le symbole DEPENDSONTEST_EXPORTS
// d�fini sur la ligne de commande. Ce symbole ne doit pas �tre d�fini dans les projets
// qui utilisent cette DLL. De cette mani�re, les autres projets dont les fichiers sources comprennent ce fichier consid�rent les fonctions 
// DEPENDSONTEST_API comme �tant import�es � partir d'une DLL, tandis que cette DLL consid�re les symboles
// d�finis avec cette macro comme �tant export�s.
#ifdef DEPENDSONTEST_EXPORTS
#define DEPENDSONTEST_API __declspec(dllexport)
#else
#define DEPENDSONTEST_API __declspec(dllimport)
#endif

// Cette classe est export�e de dependsOnTest.dll
class DEPENDSONTEST_API CdependsOnTest {
public:
	CdependsOnTest(void);
	// TODO�: ajoutez ici vos m�thodes.
};

extern DEPENDSONTEST_API int ndependsOnTest;

DEPENDSONTEST_API int fndependsOnTest(void);
