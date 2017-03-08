# encoding: utf8

# http://pypi.python.org/pypi/xlrd
import xlrd

import KspExcelUtil

TEMPLATE = """    "%(name)s":
    {
        "prefix": "%(prefix)s",
        "body":[
%(body)s
        ],
        "description": "%(description)s"
    },"""

BODY_TEMPLATE = "            \"%s\""

HEADER = """{
"##!---------------- Auto generated code: Generated by /data/Excel2Snippet.py ----------------##":{},
"## NOTE: Remove comma after last object if you re-Generated":{},
"""

FOOTER = "}"

book  = xlrd.open_workbook( 'KSP.xlsx' )
sheet = book.sheet_by_index( 0 )

rowLength = sheet.nrows

print( HEADER )
for row in range( 1, sheet.nrows ):
    name   = KspExcelUtil.getCellFromColmnName( sheet, row, KspExcelUtil.HEADER_SNIPPET_NAME ).value.strip()
    prefix = KspExcelUtil.getCellFromColmnName( sheet, row, KspExcelUtil.HEADER_SNIPPET_PREFIX ).value.strip()
    body   = KspExcelUtil.getCellFromColmnName( sheet, row, KspExcelUtil.HEADER_SNIPPET_BODY ).value.strip()
    desc   = KspExcelUtil.getCellFromColmnName( sheet, row, KspExcelUtil.HEADER_DESCRIPTION ).value.strip()

    descArray = desc.split( "\n" )
    if( len( descArray ) > 1 ):
        tmp = ""
        for i in descArray:
            tmp += i + "\\n"
        desc = tmp

    desc = desc.replace( "\"", "\\\"" )

    if( len( prefix ) == 0 or len( body ) == 0 ):
        continue

    bodyArray    = body.split( "\n" )
    bodyArrayLen = len( bodyArray )
    tmp          = ""
    if( bodyArrayLen > 1 ):
        for i in range( bodyArrayLen ):
            tmp += BODY_TEMPLATE % ( bodyArray[ i ].replace( "\"", "\\\"" ) )
            if( i + 1 < bodyArrayLen ):
                tmp += ",\n"
        body = tmp
    else:
        body = BODY_TEMPLATE % ( body.replace( "\"", "\\\"" ) )

    text = TEMPLATE % {
        "name":         name,
        "prefix":       prefix,
        "body":         body,
        "description":  desc,
    }
    print( text.encode( "utf-8" ) )

print( FOOTER )