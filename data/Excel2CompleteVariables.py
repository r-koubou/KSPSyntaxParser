# encoding: utf-8

import re

# http://pypi.python.org/pypi/xlrd
import xlrd

import KspExcelUtil

TARGET  = "../src/generated/KSPVariables.ts"

TEMPLATE = """
    "{intelliSense}":
    {{
        "description": "{description}"
    }},"""

BODY_TEMPLATE = "            \"{body}\""

HEADER = """//
// Generated by /data/Excel2CompleteVariables.py
//
export var builtinVariables = {"""
FOOTER = "};"

book  = xlrd.open_workbook( 'KSP.xlsx' )
sheet = book.sheet_by_index( 0 )

rowLength = sheet.nrows

fw = open( TARGET, 'w' )

fw.write( HEADER )
for row in range( 1, rowLength ):
    name   = KspExcelUtil.getCellFromColmnName( sheet, row, KspExcelUtil.HEADER_COMPLETE_NAME ).value.strip()
    desc   = KspExcelUtil.getCellFromColmnName( sheet, row, KspExcelUtil.HEADER_DESCRIPTION ).value.strip()

    descArray = desc.split( "\n" )
    if( len( descArray ) > 1 ):
        tmp = ""
        for i in descArray:
            tmp += i + "\\n"
        desc = tmp

    desc = desc.replace( "\"", "\\\"" )

    if( len( name ) == 0 or re.match( r"^[^a-z|A-Z|_]", name ) == None ):
        continue

    text = TEMPLATE.format(
        intelliSense = name,
        description  = desc
    )

    fw.write( text )

fw.write( "\n" )
fw.write( FOOTER )
fw.write( "\n" )
fw.close()
print( "Done: " + TARGET )
