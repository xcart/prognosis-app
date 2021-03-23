import path from 'path'
import svelte from 'rollup-plugin-svelte'
import autoPreprocess from 'svelte-preprocess'
import resolve from '@rollup/plugin-node-resolve'
import commonjs from '@rollup/plugin-commonjs'
import typescript from "@rollup/plugin-typescript"
import replace from "@rollup/plugin-replace"
import postcss from 'rollup-plugin-postcss'
import svelteSVG from "rollup-plugin-svelte-svg"

let production = false

export default {
  input: 'src/main/resources/scripts/main.js',
  output: {
	sourcemap: !production,
    name: 'bundle',
    file: 'src/main/resources/static/bundle.js',
    format: 'iife',
    globals: ['types']
  },
  plugins: [
    replace({
      preventAssignment: true,
      'process.env.NODE_ENV': JSON.stringify('development'),
    }),
    typescript({}),
    svelteSVG(),
    svelte({
      preprocess: autoPreprocess()
    }),
    postcss({
      extract: path.resolve(__dirname, './src/main/resources/static/bundle.css'),
      options: {
        includePaths: [
            path.resolve(__dirname, './node_modules'),
        ]
      }
    }),
    resolve({
      browser: true,
      jsnext: true,
      preferBuiltins: true,
      dedupe: ['svelte']
    }),
    commonjs({
    	include: /node_modules/,
    })
  ]
};