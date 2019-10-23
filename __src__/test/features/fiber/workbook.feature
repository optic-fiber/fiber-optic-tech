Feature: ClasseurRecap generation

    Scenario: Generate a workbook
        When I set the path to current directory
        And I load the existed file 'inter.json' in database
        Then I build the workbook in the file 'recapClasseur.xlsx'
